package top.yxf.mybatisplus.domain;


import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "t_user")
public class User implements Serializable {

    /**
     * AUTO(0), // 数据库id自增
     * NONE(1), // 未设置主键
     * INPUT(2), // 手动输入
     * ID_WORKER(3), // 默认的全局唯一id
     * UUID(4), // 全局唯一id uuid
     * ID_WORKER_STR(5); //ID_WORKER 字符串表示法 }
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 乐观锁Version注解
     */
    @Version
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;


}
