package validate.test;

import validate.Constraint;
import validate.FieldValidator;

/**
 * @Date: 2018/11/14 11:40
 * @Description:
 */
public class ValidateBean {


    @Constraint(notBlank = true)
    private String name;
    private String date;
    private Long lval;
    private Double dval;

    private String reg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getLval() {
        return lval;
    }

    public void setLval(Long lval) {
        this.lval = lval;
    }

    public Double getDval() {
        return dval;
    }

    public void setDval(Double dval) {
        this.dval = dval;
    }

    public static void main(String[] args) {
        FieldValidator validate = FieldValidator.newInstance();

        ValidateBean bean = new ValidateBean();
        bean.setName("");

        try {
            validate.validate(bean);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
