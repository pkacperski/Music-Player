package application;

import com.mpatric.mp3agic.*;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Read ID3 tag info from a file (if it has some), using methods from 'com.mpatric.mp3agic' package
 */

public class GetID3TagInfo {
	
	private static Image albumCover;
	private static String titleInfo;
	private static String artistInfo;
	private static String albumInfo;
	private static String songLength;
	
	public static String getTitleInfo() {
		return titleInfo;
	}
	
	public static String getArtistInfo() {
		return artistInfo;
	}
	
	public static String getAlbumInfo() {
		return albumInfo;
	}
	
	public static Image getAlbumCover() {
		return albumCover;
	}
	
	public static String getSongLength() {
		return songLength;
	}

	public static void getID3TagInfo () { 	
			Mp3File mp3file;
			try {
				mp3file = new Mp3File(ChooseFile.getFilePath()); // opens the right file

				if (mp3file.hasId3v2Tag()) {
					ID3v2 tag = mp3file.getId3v2Tag();
					//tag.setArtist("Santana"); // DELETE THIS!!!!!!
					if (tag.getTitle() == null) 
						titleInfo = "Title:\t\t   N/A";
					else 
						titleInfo = "Title:\t\t   " + tag.getTitle();
					if (tag.getArtist() == null)
						artistInfo = "Artist:\t\t   N/A";
					else	
						artistInfo = "Artist:\t\t   " + tag.getArtist();
					if (tag.getAlbum() == null)
						albumInfo = "Album:\t\t   N/A";
					else
						albumInfo = "Album:\t\t   " + tag.getAlbum();
					
					byte[] imageData = tag.getAlbumImage();
					  if (imageData != null) {						 
					    // Write image to file 
					    RandomAccessFile file = new RandomAccessFile("src/main/resources/album-artwork.jpg", "rw");
					    file.write(imageData);
					    file.close();
					  
					    ImageView albumCoverImageView = new ImageView("file:src/main/resources/album-artwork.jpg"); // the "file:" part must remain here!!
					    albumCover = albumCoverImageView.getImage();
					  }  	
					  else {
						  ImageView albumCoverImageView = new ImageView("file:src/main/resources/note.png");
						  albumCover = albumCoverImageView.getImage();
					  }
					  
					  int temp = (int)mp3file.getLengthInSeconds();
					  songLength = String.valueOf(temp/60) + ':';
					  if (temp%60 >= 10)	// to avoid printing "4:7" instead of "4:07"
						  songLength += String.valueOf(temp%60);
					  else
						  songLength += '0' + String.valueOf(temp%60);
					 
				}
			} catch (UnsupportedTagException | InvalidDataException | IOException e) {
				e.printStackTrace();
				System.out.println("Error in mp3agic functioning: " + e.toString()) ;
			}
	}
				

	public GetID3TagInfo() {
		try{
			getID3TagInfo();
		} catch (Exception e){
			System.out.println("Error in GetID3TagInfo class constructor: " + e.toString());
		}
	}
	
}