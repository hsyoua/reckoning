package cn.yugutou.reckoning.dto.resp;

import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.MessageHeader;
import lombok.Data;

import java.io.BufferedInputStream;
import java.io.Serializable;
import java.net.URL;
@Data
public class RegisterResp implements Serializable {
    private boolean result;
}
