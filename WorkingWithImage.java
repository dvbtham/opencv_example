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

		Mat imageMat = Imgcodecs.imread(IMG_URL);
		Mat imageGray = new Mat(imageMat.rows(), imageMat.cols(), imageMat.type());

		if (imageMat.empty()) {
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
					Imgproc.cvtColor(imageMat, imageGray, Imgproc.COLOR_RGB2GRAY);
					imageDstView.setIcon(new ImageIcon(MatToBufferedImage.Converter(imageGray, false)));
					imageSrcView.setText("=>");
					break;
				case 2:
					
					Mat imageBinary = new Mat();
					
					if (imageGray.empty())
					{
						System.out.println("Could not open or find the image\n");
						break;
					}
				 
					Imgproc.threshold(imageGray, imageBinary, 128, 255, Imgproc.THRESH_BINARY);
					
					imageDstView.setIcon(new ImageIcon(MatToBufferedImage.Converter(imageBinary, true)));
					imageSrcView.setText("=>");
					break;
				}
			}
		});


		imageSrcView.setIcon(new ImageIcon(MatToBufferedImage.Converter(imageMat, false)));

		add(colorSpaceCbb);
		add(imageSrcView);
		add(imageDstView);

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
