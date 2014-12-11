package org.mynah.follower;

import java.util.List;
import org.mynah.follower.model.Follower;

public class SkillUtil {

    public static final String[] SKILLS = { "野生怪物入侵", "重击", "群体伤害", "魔法减益", "危险区域", "爪牙围攻", "强力法术", "致命爪牙", "限时战斗" };

    public static int getSkillIndex(String skillName) {
        int index = -1;
        for (int i = 0; i < SKILLS.length; i++) {
            if (SKILLS[i].equals(skillName)) {
                index = i;
            }
        }
        return index;
    }

    public static String count(String[] followerIndexs, List<Follower> followers) {
        StringBuilder sb = new StringBuilder();
        int[] rank = new int[SKILLS.length];
        for (int i = 0; i < followerIndexs.length; i++) {
            int index = Integer.parseInt(followerIndexs[i]);
            Follower follower = followers.get(index - 1);
            List<Integer> skills = follower.getSkills();
            for (Integer skill : skills) {
                rank[skill] = rank[skill] + 1;
            }
        }
        for (int i = 0; i < rank.length; i++) {
            sb.append(rank[i]);
        }
        return sb.toString();
    }
}
