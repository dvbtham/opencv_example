package openCV;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class WorkingWithImage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JComboBox<ColorSpace> colorSpaceCbb;
	private JLabel imageSrcView;
	private JLabel imageDstView;
	public boolean goToNext = true;
	public final String IMG_URL = "D:/dev/java/openCV/src/images/thamdavies.jpg";

	public WorkingWithImage() {
		super("Image Demo");

		setLayout(new FlowLayout(FlowLayout.LEFT));
		imageSrcView = new JLabel();
		imageDstView = new JLabel();
		colorSpaceCbb = new JComboBox<ColorSpace>(
				new DefaultComboBoxModel<ColorSpace>(ColorSpace.PopulateColorSpaces()));

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat srcImg = Imgcodecs.imread(IMG_URL);
		Mat destinationImage = new Mat(srcImg.rows(), srcImg.cols(), srcImg.type());

		if (srcImg.empty()) {
			System.out.println("Image not found");
			goToNext = false;
			return;
		}

		RenderCombobox();

		colorSpaceCbb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				ColorSpace obj = (ColorSpace) cb.getSelectedItem();

				switch (obj.getValue()) {
				case 1:
					ImageConverter(srcImg, destinationImage, Imgproc.COLOR_RGB2GRAY);
					break;

				case 3:
					ImageConverter(srcImg, destinationImage, Imgproc.COLOR_RGB2HSV);
					break;

				case 4:
					ImageConverter(srcImg, destinationImage, Imgproc.COLOR_RGB2Lab);
					break;
					
				case 5:
					ImageConverter(srcImg, destinationImage, Imgproc.COLOR_RGB2HLS_FULL);
					break;

				case 2:

					Imgproc.cvtColor(srcImg, destinationImage, Imgproc.COLOR_RGB2GRAY);

					Mat imageBinary = new Mat();

					if (destinationImage.empty()) {
						System.out.println("Could not open or find the image\n");
						break;
					}

					Imgproc.threshold(destinationImage, imageBinary, 128, 255, Imgproc.THRESH_BINARY);
					ShowImage(imageBinary);

					break;
				}
			}
		});

		imageSrcView.setIcon(new ImageIcon(MatToBufferedImage.Converter(srcImg)));

		add(colorSpaceCbb);
		add(imageSrcView);
		add(imageDstView);

	}

	private void ImageConverter(Mat srcImg, Mat destinationImage, int colorSpaceValue) {
		Imgproc.cvtColor(srcImg, destinationImage, colorSpaceValue);
		ShowImage(destinationImage);
	}

	private void ShowImage(Mat srcImg) {

		imageDstView.setIcon(new ImageIcon(MatToBufferedImage.Converter(srcImg)));
		imageSrcView.setText("=>");
	}

	private void RenderCombobox() {
		colorSpaceCbb.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof ColorSpace) {
					ColorSpace obj = (ColorSpace) value;
					setText(obj.getName());
				}
				return this;
			}
		});
	}

}
