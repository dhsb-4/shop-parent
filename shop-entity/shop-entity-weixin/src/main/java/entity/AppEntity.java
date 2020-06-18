package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppEntity {
    public String appId;

    public String appName;

    public AppEntity(String appId,String appName){
        this.appId=appId;
        this.appName=appName;
    }
}
