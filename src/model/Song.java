package model;

/**
 * Created by yugantjoshi on 2/6/17.
 */
public class Song
{
    private String name, artist, album;
    private int year;

    public Song(String name, String artist, String album, int year){
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }
    public Song(String name, String artist, String album){
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = -1;
    }
    public Song(String name, String artist, int year){

    }
    public Song(String name, String artist){
        this.name = name;
        this.artist = artist;
        this.album = null;
        this.year = -1;
    }

    public String getName(){
        return this.name;
    }
    public String getArtist(){
        return this.artist;
    }
    public String getAlbum(){
        return this.album;
    }
    public int getYear(){
        return this.year;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    public void setAlbum(String album){
        this.album = album;
    }
    public void setYear(int year){
        this.year = year;
    }
    public String toString(){
        return this.name;
    }
}
