package xyz.funnyboy.excel;

import com.alibaba.excel.EasyExcel;

/**
 * 读取测试
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 09:37:18
 */
public class ReadTest
{
    public static void main(String[] args) {
        String fileName = "D:\\workspace-mine\\10-SiliconValleyClassroom\\ggkt_parent\\service\\service_vod\\src\\main\\resources\\excel\\ReadTest.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel
                .read(fileName, User.class, new ExcelListener())
                .sheet()
                .doRead();
    }
}
