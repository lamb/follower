package org.mynah.follower;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mynah.follower.model.Follower;
import org.mynah.follower.model.Mission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class App {

    public static Logger logger = LoggerFactory.getLogger(App.class);
    public static final int QUANTITY = 3;

    public static void main(String[] args) throws IOException {
        App app = new App();
        String missionFilePath = "classpath:mission.xlsx";
        List<Mission> missions = app.initMission(missionFilePath);
        String followerFilePath = "classpath:mine20141210.xlsx";
        List<Follower> followers = app.initFollower(followerFilePath);
        // 取出所有的随从组合
        ArrayList<String> combines = CombineUtil.combine(followers.size(), QUANTITY);
        System.out.println(followers.size());
        System.out.println(combines);
        // 循环所有的任务
        for (Mission mission : missions) {
            String skill = mission.getSkill();
            // 循环所有的组合
            for (String combine : combines) {
                String[] followerIndexs = combine.split(CombineUtil.SPLIT);
                String combineSkill = SkillUtil.count(followerIndexs, followers);
                if (skill.equals(combineSkill)) {
                    logger.info("{}, {}, {}", mission.getId(), mission.getName(), combineSkill);
                }
            }
        }

    }

    public List<Mission> initMission(String missionFilePath) throws IOException {
        List<Mission> missions = new ArrayList<Mission>();
        File requestFile = ResourceUtils.getFile(missionFilePath);
        FileInputStream fis = new FileInputStream(requestFile);
        XSSFWorkbook workBook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workBook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        for (int i = 1; i <= rowNum; i++) {
            Mission mission = new Mission();
            XSSFRow row = sheet.getRow(i);
            mission.setId(row.getCell(0).getStringCellValue());
            mission.setName(row.getCell(2).getStringCellValue());
            StringBuilder skill = new StringBuilder();
            for (int j = 14; j < 14 + 9; j++) {
                skill.append(row.getCell(j) == null ? 0 : row.getCell(j));
            }
            mission.setSkill(skill.toString());
            logger.info(mission.toString());
            missions.add(mission);
        }
        return missions;
    }

    public List<Follower> initFollower(String followerFilePath) throws IOException {
        List<Follower> followers = new ArrayList<Follower>();
        File requestFile = ResourceUtils.getFile(followerFilePath);
        FileInputStream fis = new FileInputStream(requestFile);
        XSSFWorkbook workBook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workBook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        for (int i = 1; i <= rowNum; i++) {
            Follower follower = new Follower();
            XSSFRow row = sheet.getRow(i);
            follower.setName(row.getCell(0).getStringCellValue());
            this.addSkill(row.getCell(6), follower);
            this.addSkill(row.getCell(7), follower);
            logger.info(follower.toString());
            followers.add(follower);
        }
        return followers;
    }

    public void addSkill(XSSFCell cell, Follower follower) {
        if (cell != null) {
            String skillName = cell.getStringCellValue();
            if (null != skillName) {
                int index = SkillUtil.getSkillIndex(skillName.trim());
                if (index >= 0) {
                    follower.getSkills().add(index);
                } else {
                    logger.error("{} not found!");
                }
            }
        }
    }
}
