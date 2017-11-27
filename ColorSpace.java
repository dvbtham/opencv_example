package openCV;

public class ColorSpace {
	private int Value;
	private String Name;

	public ColorSpace(int Value, String Name) {
		this.Value = Value;
		this.Name = Name;
	}

	public String getName() {
		return this.Name;
	}

	public int getValue() {
		return this.Value;
	}

	public static ColorSpace[] PopulateColorSpaces() {
		ColorSpace clsp1 = new ColorSpace(1, "Chuyển sang ảnh xám");
		ColorSpace clsp2 = new ColorSpace(2, "Chuyển sang ảnh nhị phân");
		ColorSpace clsp3 = new ColorSpace(3, "Chuyển sang HSV");
		ColorSpace clsp4 = new ColorSpace(4, "Chuyển sang Lab");
		ColorSpace clsp5 = new ColorSpace(5, "Chuyển sang HSL");
		ColorSpace[] persons = new ColorSpace[] { clsp1, clsp2, clsp3, clsp4, clsp5 };
		return persons;
	}
}
