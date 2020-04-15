package top.yxf.mybatisplus.common;

/**
 * @author 一乡风
 * 通用响应
 */
public class ResponseVo<T> {


    private Integer status;

    private T data;

    private String msg;

    public static <T> ResponseVo<T> success(T data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setData(data);
        responseVo.setStatus(0);
        responseVo.setMsg("成功");
        return responseVo;
    }

    public static <T> ResponseVo<T> fail(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(1);
        responseVo.setMsg(msg);
        return responseVo;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
