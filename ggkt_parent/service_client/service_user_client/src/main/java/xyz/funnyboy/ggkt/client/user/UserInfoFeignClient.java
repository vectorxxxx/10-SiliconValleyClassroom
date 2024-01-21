package xyz.funnyboy.ggkt.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.ggkt.model.user.UserInfo;

@Component
@FeignClient(value = "service-user")
public interface UserInfoFeignClient
{

    @GetMapping("/admin/user/userInfo/inner/getById/{id}")
    UserInfo getById(
            @PathVariable
                    Long id);
}
