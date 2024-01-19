package xyz.funnyboy.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * 写入测试
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 09:40:31
 */
public class WriteTest
{
    public static void main(String[] args) {
        String fileName = "D:\\workspace-mine\\10-SiliconValleyClassroom\\ggkt_parent\\service\\service_vod\\src\\main\\resources\\excel\\WriteTest.xlsx";
        EasyExcel
                .write(fileName, User.class)
                .sheet("用户")
                .doWrite(data());
    }

    private static List<User> data() {
        final List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("name" + i);
            userList.add(user);
        }
        return userList;
    }
}
