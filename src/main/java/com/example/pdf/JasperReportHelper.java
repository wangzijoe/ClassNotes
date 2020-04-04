package com.example.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/jasperreports/")
public class JasperReportHelper {

	private static final String DATA_DIR = File.separator + "data";
	private static final String TEMPLATE_DIR = File.separator + "template";
	private static final String SRC_MAIN_JAVA = File.separator + "src" + File.separator + "main" + File.separator
			+ "java";

	@ResponseBody
	@RequestMapping(value = "print/{pdfTemplate}/{pdfJson}", method = RequestMethod.GET)
	public void printPDF(HttpServletResponse response, @PathVariable("pdfTemplate") String pdfTemplate,
			@PathVariable("pdfJson") String pdfJson) {

		System.err.println("pdfTemplate: " + pdfTemplate);
		System.err.println("pdfJson: " + pdfJson);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);

		String pdfJsonPath = this.getSrcFile() + DATA_DIR + File.separator + pdfJson;
		String pdfTemplatePath = this.getSrcFile() + TEMPLATE_DIR + File.separator + pdfTemplate;
		File pdfJsonFile = new File(pdfJsonPath);
		File pdfTemplateFile = new File(pdfTemplatePath);
		InputStream byteArrayDataInput = null;
		try {
			String realPdfJson = Files.lines(pdfJsonFile.toPath(), StandardCharsets.UTF_8).reduce("",
					(prev, line) -> prev + line + System.getProperty("line.separator"));
			byteArrayDataInput = new ByteArrayInputStream(realPdfJson.getBytes("UTF-8"));
			JsonDataSource dataSource = new JsonDataSource(byteArrayDataInput);

			String realPdfTemplate = Files.lines(pdfTemplateFile.toPath(), StandardCharsets.UTF_8).reduce("",
					(prev, line) -> prev + line + System.getProperty("line.separator"));
			byteArrayDataInput = new ByteArrayInputStream(realPdfTemplate.getBytes("UTF-8"));
			JasperReport report = JasperCompileManager.compileReport(byteArrayDataInput);
			
			Map<String,Object> parameters = new HashMap<String, Object>(16);
			JasperPrint fillReport = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfStream(fillReport, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				byteArrayDataInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取当前类的所在工程路径
	 * 
	 * @return /D:/workSapces/ClassNotes/target/classes/
	 */
	private String getClassPath() {
		return this.getClass().getResource("/").getPath();
	}

	/**
	 * 获取当前类的绝对路径
	 * 
	 * @return /D:/workSapces/ClassNotes/target/classes/com/example/pdf/
	 */
	private String getAbsoluatePath() {
		return this.getClass().getResource("").getPath();
	}

	/**
	 * 获取当前类的所在工程路径
	 * 
	 * @return D:\workSapces\ClassNotes
	 */
	private String getClassProjectPath() {
		File directory = new File("");// 参数为空
		try {
			return directory.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取SRC目录下的路径（绝对路径）
	 * 
	 * @return D:\workSapces\ClassNotes\com\example\pdf\JasperReportHelper
	 */
	private String getSrcFile() {
		String className = new File(this.getClass().getName()).getPath();
		// className = com.example.pdf.JasperReportHelper
		className = className.substring(0, className.lastIndexOf("."));
		return getClassProjectPath() + SRC_MAIN_JAVA + File.separator
				+ String.join(File.separator, className.split("\\."));
	}

}
