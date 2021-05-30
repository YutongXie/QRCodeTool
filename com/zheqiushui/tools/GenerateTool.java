package zheqiushui.tools;

import org.apache.commons.lang3.StringUtils;
import zheqiushui.entity.VCard;
import zheqiushui.service.QRCodeGeneratorService;

public class GenerateTool {
    private static final String QR_CODE_OUTPUT_IMAGE_PATH = "/OutputQRCode.png";

    private VCard setupVCard() {
        VCard vCard = new VCard();
        vCard.setName("车主");
        vCard.setTelPhone("19999999999");
        vCard.setNote("挪车请拨电话");
        return vCard;
    }

    private String getAbsoluteOutputDir(String subDir) {
        String rootDir = System.getProperty("user.dir");
        if (StringUtils.isBlank(subDir)) {
            return rootDir;
        } else {
            return rootDir + subDir;
        }
    }

    private String generateContent(VCard vCard) {
        if (vCard == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BEGIN:VCARD").append("\n");
        stringBuilder.append("VERSION:3.0").append("\n");
        if (StringUtils.isNotBlank(vCard.getName())) {
            stringBuilder.append("N:").append(vCard.getName()).append("\n");
        }

        if (StringUtils.isNotBlank(vCard.getTelPhone())) {
            stringBuilder.append("TEL:").append(vCard.getTelPhone()).append("\n");
        }

        if (StringUtils.isNotBlank(vCard.getNote())) {
            stringBuilder.append("NOTE:").append(vCard.getNote()).append("\n");
        }

        stringBuilder.append("END:VCARD");
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        GenerateTool tool = new GenerateTool();
        QRCodeGeneratorService generator = new QRCodeGeneratorService();
        try {
            String content = tool.generateContent(tool.setupVCard());
            String outputDir = tool.getAbsoluteOutputDir(QR_CODE_OUTPUT_IMAGE_PATH);
            generator.generateQRCodeImage(content, 350, 350, outputDir);
        } catch (Exception e) {
            System.out.println("Failed to generate QR Code, Exception :: " + e.getMessage());
        }
    }
}
