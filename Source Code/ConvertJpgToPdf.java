import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertJpgToPdf {

	public static void main(String arg[]) throws Exception {

		File root = new File("Desktop");

		String outputFile = "some.pdf";

		List<String> files = new ArrayList<String>();
		files.add("0.png");
		files.add("1.png");
		files.add("2.png");
		files.add("3.png");
		files.add("4.png");
		files.add("5.png");
		files.add("6.png");
		files.add("7.png");
		files.add("8.png");
		files.add("9.png");

		Document document = new Document();

		PdfWriter.getInstance(document, new FileOutputStream(new File(root, outputFile)));
		document.open();

		for (String f : files) {
			document.newPage();
			Image image = Image.getInstance(new File(root, f).getAbsolutePath());
			image.setAbsolutePosition(0, 0);
			image.setBorderWidth(0);
			image.scaleAbsoluteHeight(PageSize.A4.getHeight());
			image.scaleAbsoluteWidth(PageSize.A4.getWidth());
			document.add(image);
		}

		document.close();

	}
}