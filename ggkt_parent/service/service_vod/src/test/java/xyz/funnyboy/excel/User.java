package xyz.funnyboy.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户对象
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 09:32:56
 */
@Data
public class User
{
    @ExcelProperty(value = "用户编号",
                   index = 0)
    private int id;

    @ExcelProperty(value = "用户名称",
                   index = 1)
    private String name;
}
