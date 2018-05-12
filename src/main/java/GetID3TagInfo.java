import java.io.FileInputStream;
import java.io.InputStream;

public class GetID3TagInfo {
	public GetID3TagInfo() {
		try{
			getID3TagInfo();
		} catch (Exception e){
			System.out.println("Error in GetID3TagInfo class constructor: " + e.toString());
		}
	}
	
	public static void getID3TagInfo () { 	
    	try {		
			int size = (int)ChooseFile.getFile().length();
			InputStream in = new FileInputStream(ChooseFile.getFile());
			in.skip(size - 128);
			byte[] last128 = new byte[128];
			in.read(last128);
			String id3 = new String(last128);
			String tag = id3.substring(0, 3);
			if (tag.equals("TAG")) {
				Interface.getTitleInfo().setText("Title: " + id3.substring(3, 33));
				Interface.getArtistInfo().setText("Artist: " + id3.substring(33, 63));
				Interface.getAlbumInfo().setText("Album: " + id3.substring(63, 93));
			} else
				System.out.println("This file does not contain ID3 information");
				in.close();
		} catch (Exception e) {
			System.out.println("Error fetching ID3 tag: " + e.toString());
		}	
    }
	
	
}
