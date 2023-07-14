import java.text.DecimalFormat;
import java.util.Comparator;

public class Music {
	private String albumTitle;
	private String artist;
	private String mediaFormat;
	private String releasedDate;
	private int numSongs;
	private double albumPrice; //Price of the Album
	private int numAlbums; //Number of albums being sold/bought
	
	/**
	 * Default constructor for music, calls the parent class default constructor
	 * assign default values to the all private variables
	 */
	public Music() {
		this.albumTitle = "No Title";
		this.artist = "No Artist";
		this.mediaFormat = "No Format";
		this.releasedDate = "No Release Date";
		this.numSongs = 0;
		this.albumPrice = 0.0;
		this.numAlbums = 0;
	}

	/**
	 * Copy Constructor for Music
	 * 
	 * 
	 * @param music - takes a Music object and Creates a deep copy
	 */
	public Music(Music music) {
		this.albumTitle = music.albumTitle;
		this.artist = music.artist;
		this.mediaFormat = music.mediaFormat;
		this.releasedDate = music.releasedDate;
		this.numSongs = music.numSongs;
		this.albumPrice = music.getAlbumPrice();
		this.numAlbums = music.getNumAlbums();
	}
	/**
	 * Two argument constructor, takes an album title and artist's name, 
	 * calls the parent class' two-argument constructor and
	 * initializes an item on the selling list. 
	 * 
	 * @param albumTitle - title of the album
	 * @param artist - name of the artist
	 */
	public Music(String albumTitle, String artist) {
		this.albumTitle = albumTitle;
		this.artist = artist;
		this.mediaFormat = "No Format";
		this.releasedDate = "No Release Date";
		this.numSongs = 0;
		this.albumPrice = 0.0;
		this.numAlbums = 0;
	}
	/**
	 * Seven argument constructor, takes an album title and artist's name, 
	 * calls the parent class' seven-argument constructor and
	 * initializes an item on the selling list. 
	 * 
	 * @param albumTitle - title of the album
	 * @param artist - name of the artist
	 * @param disk - either CD or LP
	 * @param releasedDate - album released date
	 * @param numSongs - number of songs in the album
	 * @param albumPrice - price of the album
	 * @param numAlbums - number of the albums we have in stock
	 * 
	 */
	
	public Music(String albumTitle, String artist, String mediaFormat, String releasedDate,
			int numSongs, double albumPrice, int numAlbums) {
		this.albumTitle = albumTitle;
		this.artist = artist;
		this.mediaFormat = mediaFormat.toUpperCase();
		this.releasedDate = releasedDate;
		this.numSongs = numSongs;
		this.albumPrice = albumPrice;
		this.numAlbums = numAlbums;
	}
	
	/** ACCESSORS */
	
	/**
	 * getAlbumTitle - receives nothing
	 * @return - the title of the album
	 */
	
	public String getAlbumTitle() {
		return albumTitle;
	}
	
	/**
	 * getArtist - receives nothing
	 * @return - the name of the artist
	 */
	
	public String getArtist() {
		return artist;
	}
	
	/**
	 * getMediaformat - receives nothing
	 * @return - type of the media format
	 */
	
	public String getMediaFormat() {
		return mediaFormat;
	}
	
	/**
	 * getReleasedDate - receives nothing
	 * @return - the released date of the album
	 */
	
	public String getReleasedDate() {
		return releasedDate;
	}
	
	/**
	 * getNumsongs - receives nothing
	 * @return - the number of the songs in the album
	 */
	
	public int getNumSongs() {
		return numSongs;
	}
	
	/**
	 * getAlbumPrice - receives nothing
	 * @return - the price of album
	 */
	
	public double getAlbumPrice() {
		return albumPrice;
	}

	/**
	 * getNumAlbums - receives nothing
	 * @return - the number of albums in stock
	 */
	
	public int getNumAlbums() {
		return numAlbums;
	}


	/** MUTATORS */
	
	/**
	 * setAlbumTitle - updates the title of the album
	 * @param albumTitle - name used to update the album title
	 */
	
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	
	/**
	 * setArtist - updates the name of the artist
	 * @param artist - name used to update the artist's name
	 */
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	/**
	 * setMediaFormat - updates the media format
	 * @param mediaFormat - type used to update the media format
	 */
	
	public void setMediaFormat(String mediaFormat) {
		this.mediaFormat = mediaFormat.toUpperCase();
	}
	
	/**
	 * setReleasedDate - updates the released date of the album
	 * @param albumTitle - date used to update the album released date
	 */
	
	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}
	
	/**
	 * setNumsongs - updates the number of the songs in the album
	 * @param numSongs - integer used to update the number of songs
	 * in the album
	 */
	
	public void setNumSongs(int numSongs) {
		this.numSongs = numSongs;
	}
	
	/**
	 * setAlbumPrice - updates the price of the album
	 * @param albumPrice - number used to update the album price
	 */
	
	public void setAlbumPrice(double albumPrice) {
		this.albumPrice = albumPrice;
	}

	/**
	 * setNumAlbums - updates the number of albums in stock
	 * 
	 * @param numAlbums - number used to update the number of 
	 * albums we have in stock
	 */
	public void setNumAlbums(int numAlbums) {
		this.numAlbums = numAlbums;
	}
	/**
	 * updateNumAlbums - updates the number of albums in stock
	 * 
	 * @param numAlbums - number used to update the number of 
	 * albums we have in stock
	 */
	public void updateNumAlbums(int numAlbums) {
		this.numAlbums += numAlbums;
	}

	 /**
	  * formatMusicDataForFileWrite - takes nothing
	  * 
	  * prints to the .txt file the music data in the format.
	  * returns 
	  */
	
	public String formatMusicDataForFileWrite() {
		DecimalFormat d = new DecimalFormat("###,###.00");

		String data = "";
		data += albumTitle + "\n";
		data += artist + "\n";
		data += numSongs + "\n";
		data += releasedDate + "\n";
		data += mediaFormat + "\n";
		data += d.format(albumPrice) + "\n";
		data += numAlbums + "\n";

		return data;
	}

	/**
	 * toString - takes nothing
	 * 
	 * Creates a String of the album information in the format
	 * 
	 */
	
	@Override public String toString() {
        DecimalFormat d = new DecimalFormat("###,###.00");
        return "\nAlbum Title: " + albumTitle
                + "\nArtist: " + artist
                + "\nDisk: " + mediaFormat
                + "\nReleased Date: " + releasedDate
                + "\nNumber of Songs: " + numSongs
                + "\nAlbum Price: $" + d.format(albumPrice)
				+ "\nNumber of Units: " + numAlbums;
        
    }
	
	/**
	 * toStringOrder - takes nothing
	 * 
	 * Creates a String of the album information in the format
	 * 
	 */
	
	public String toStringOrder() {
        DecimalFormat d = new DecimalFormat("###,###.00");
        return "\nAlbum Title: " + albumTitle
                + "\nArtist: " + artist
                + "\nDisk: " + mediaFormat
                + "\nReleased Date: " + releasedDate
                + "\nNumber of Units Purchased: " + numSongs
                + "\nAlbum Price: $" + d.format(albumPrice);
        
    }
	
	/**
	 * equals 
	 * 
	 * @param - an object
	 * returns a boolean - True if the object is a music object
	 * that has the same album title and artist as this music object.
	 * 
	 * Returns false otherwise
	 */
	 @Override public boolean equals(Object o) {
	        if (o == this) {
	            return true;
	        } else if (!(o instanceof Music)) {
	            return false;
	        }
	        else {
	            Music m = (Music) o;
	            return m.albumTitle.equals(albumTitle) && m.artist.equals(artist);
	        }
	        
	    }
}

class AlbumTitleComparator implements Comparator<Music> {
	/**
	 * Compares the two mutual fund accounts by name of the fund uses the String
	 * compareTo method to make the comparison
	 * 
	 * @param account1 the first MutualFundAccount
	 * @param account2 the second MutualFundAccount
	 */
	@Override
	public int compare(Music account1, Music account2) {
		return account1.getAlbumTitle().compareTo(account2.getAlbumTitle());
	}
} 

class ArtistComparator implements Comparator<Music> {
	/**
	 * Compares the two mutual fund accounts by total value determines total value
	 * as number of shares times price per share uses the static Double compare
	 * method to make the comparison
	 * 
	 * @param account1 the first MutualFundAccount
	 * @param account2 the second MutualFundAccount
	 */
	@Override
	public int compare(Music account1, Music account2) {
	
		return account1.getArtist().compareTo(account2.getArtist());

	}

}
