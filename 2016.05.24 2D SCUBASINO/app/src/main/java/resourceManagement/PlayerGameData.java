package resourceManagement;

import java.util.HashMap;

import constant.Gift;

/**
 * Created by gerard on 04/05/2016.
 */
public class PlayerGameData {
    private HashMap<Gift,Integer> rewards;

    public PlayerGameData(){
        rewards = new HashMap<>();
    }

    public void addReward(Gift gift){
        if(rewards.containsKey(gift)){
            int v = rewards.get(gift);
            v++;
            rewards.put(gift,new Integer(v));
        }
        else{
            rewards.put(gift,new Integer(1));
        }
    }

    public boolean hasReward(Gift gift){
        return rewards.containsKey(gift);
    }

    public String rewardReport(){
        StringBuilder report = new StringBuilder();
        if(rewards.isEmpty()){
            return "Empty old bean!";
        }
        for (Gift gift : rewards.keySet()) {
            report.append(gift.toString()+": "+rewards.get(gift).toString()+"\n");
        }
        return report.toString();
    }
}
