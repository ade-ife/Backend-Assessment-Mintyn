package Niji.Backend.Assessment.Mintyn.Pojo.Response;

public class ResponseObject {

    public ResponseObject(String responseDescription, String description) {
        this.setResponseDescription(responseDescription);
        this.description = description;
    }

    public ResponseObject() {

    }

    private String responseCode;
    private String responseDescription;
    private String description;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        try {
            EnumResponseCodes enumValue = EnumResponseCodes.valueOf(responseDescription);
            this.setResponseCode(enumValue.getValue());
        }catch (Exception ex){

        }
        this.responseDescription = responseDescription.replace("_", " ");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "responseCode='" + responseCode + '\'' +
                ", responseDescription='" + responseDescription + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

