package com.github.yogurt.cg;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

/**
 * @author jtwu
 */
@Slf4j
public class CommonPageParser {


	private final static String CONTENT_ENCODING = "UTF-8";
	private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

	static {


// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:

		cfg.setClassForTemplateLoading(CommonPageParser.class, "/");

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
		cfg.setDefaultEncoding(CONTENT_ENCODING);

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//        cfg.setClassicCompatible(true);

	}

	static void writerPage(Map map, String templateName, String fileDirPath, String targetFile) throws Exception {
		File file = new File(fileDirPath + targetFile);
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
		}
		Template temp = cfg.getTemplate(templateName);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
		temp.process(map, writer);
		writer.flush();
		writer.close();
		fos.close();
		log.info("成功创建 " + file.getAbsolutePath());
	}

}