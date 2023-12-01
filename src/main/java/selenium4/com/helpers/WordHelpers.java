package selenium4.com.helpers;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import selenium4.com.exceptions.InvalidPathForWordException;
import selenium4.com.utils.LogUtils;

public class WordHelpers {

	private XWPFDocument document;
	private XWPFParagraph p;
	private XWPFRun pRun;
	
    public WordHelpers() {
    	document = new XWPFDocument();
    }

    public void setParagraph(ParagraphAlignment align, int indent) {
        if (document == null) {
            try {
                LogUtils.info("XWPFDocument instance is not instantiated.");
                throw new InvalidPathForWordException("XWPFDocument instance is not instantiated.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        p = document.createParagraph();
        p.setAlignment(align);
        p.setIndentationFirstLine(indent);
    }

    public void setContent(String text, boolean isBold, int fontSize, String fontFamily, boolean addBreak) {
        if (p == null) {
            try {
                LogUtils.info("XWPFParagraph instance is not instantiated.");
                throw new InvalidPathForWordException("XWPFParagraph instance is not instantiated.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pRun = p.createRun();
        pRun.setBold(isBold);
        pRun.setFontSize(fontSize);
        pRun.setFontFamily(fontFamily);
        pRun.setText(text);
        if (addBreak) {
        	pRun.addBreak();
        }
    }

    public void setContent(String text, String hyperlink, boolean isBold, int fontSize, String fontFamily, boolean addBreak) {
        if (p == null) {
            try {
                LogUtils.info("XWPFParagraph instance is not instantiated.");
                throw new InvalidPathForWordException("XWPFParagraph instance is not instantiated.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pRun = p.createHyperlinkRun(hyperlink);
        pRun.setBold(isBold);
        pRun.setFontSize(fontSize);
        pRun.setFontFamily(fontFamily);
        pRun.setText(text);
        if (addBreak) {
        	pRun.addBreak();
        }
    }

    public void writeToWordFile(String wordPath) {
        LogUtils.info("Set Word File: " + wordPath);
        try {
        	File f = new File(wordPath);
            if (!f.exists()) {
                try {
                    LogUtils.info("File Word path not found.");
                    throw new InvalidPathForWordException("File Word path not found.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (document == null) {
                try {
                    LogUtils.info("XWPFDocument instance is not instantiated.");
                    throw new InvalidPathForWordException("XWPFDocument instance is not instantiated.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        	FileOutputStream out = new FileOutputStream(wordPath);
        	document.write(out);
        	out.close();
        	document.close();            
        } catch (Exception e) {
            e.getMessage();
            LogUtils.error(e.getMessage());
        }
    }

}
