package com.example.zyq.kaminotetest.Utils;

import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ToolbarController {
    public static List<Toolbar> toolbars = new ArrayList<>();

    public static void addToolbar(Toolbar toolbar){
            if(!ToolbarController.HasToolbar(toolbar)){
                toolbars.add(toolbar);
            }
    }

    public static void removeToolbar(Toolbar toolbar){
        toolbars.remove(toolbar);
    }

    public static void removeAll(List<Toolbar> toolbars){
        //toolbars.removeAll(toolbars);
    }

    public static boolean HasToolbar(Toolbar toolbar){
        for(int i = 0; i< toolbars.size();i++) {
            if (toolbar.getId() == toolbars.get(i).getId()) {
                return true;
            }
        }
        return false;
    }
}
