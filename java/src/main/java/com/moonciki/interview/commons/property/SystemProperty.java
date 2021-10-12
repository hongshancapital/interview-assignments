package com.moonciki.interview.commons.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SystemProperty {

    /** 项目根目录 **/
    private String projectRoot;

    /** 项目外部文件存放目录 **/
    @Value("${system.outFolder:}")
    private String outFolder;

    public String getProjectRoot(){
        projectRoot = this.getClass().getResource("/").getPath();
        System.out.println(projectRoot);
        return projectRoot;
    }

    public String getOutFolder() {
        //outFolder = getProjectRoot() + "/out_folder";
        return outFolder;
    }

}
