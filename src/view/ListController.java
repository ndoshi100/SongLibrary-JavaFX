package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Song;

import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.net.InterfaceAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created by Nimit Doshi and Yugant Joshi
 */
public class ListController implements Initializable {
    @FXML
    private ListView songs_listview;
    @FXML
    Button add_button, edit_button, delete_button, confirm_button, cancel_button;
    @FXML
    TextField name_detail, artist_detail, album_detail, year_detail;

    private ObservableList<String> songsObservableList = FXCollections.observableArrayList();
    ArrayList<Song> songsList = new ArrayList<Song>();

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        name_detail.setText("");
        name_detail.setEditable(false);

        artist_detail.setText("");
        artist_detail.setEditable(false);

        album_detail.setText("");
        album_detail.setEditable(false);

        year_detail.setText("");
        year_detail.setEditable(false);

        confirm_button.setDisable(true);
        cancel_button.setDisable(true);

        populateList();
    }

    public void addSongHandle() {

        name_detail.setText("");
        artist_detail.setText("");
        album_detail.setText("");
        year_detail.setText("");

        name_detail.setPromptText("...Enter Song...");
        artist_detail.setPromptText("...Enter Artist...");
        album_detail.setPromptText("...Enter Album...");
        year_detail.setPromptText("...Enter Year...");

        name_detail.setEditable(true);
        artist_detail.setEditable(true);
        album_detail.setEditable(true);
        year_detail.setEditable(true);

        confirm_button.setDisable(false);
        cancel_button.setDisable(false);

        confirm_button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                String name = name_detail.getText();
                String artist = artist_detail.getText();
                String album = album_detail.getText();
                String year = year_detail.getText();
                Song s;

                //Constructor with all 4
                if (!album.equals("") && !year.equals("")) {
                    s = new Song(name, artist, album, Integer.parseInt(year));
                    songsList.add(s);
                }
                //Song and Artist
                else if (album.equals("") && year.equals("")) {
                    s = new Song(name, artist);
                    songsList.add(s);
                }
                //No album
                else if (album.equals("")) {
                    s = new Song(name, artist, Integer.parseInt(year));
                    songsList.add(s);
                }
                //No year
                else if (year.equals("")) {
                    s = new Song(name, artist, album);
                    songsList.add(s);
                }
                songsObservableList.add(songsList.get(songsList.size() - 1).getName());

                //sortSongs()

                songs_listview.getSelectionModel().selectLast();

                name_detail.setEditable(false);
                artist_detail.setEditable(false);
                album_detail.setEditable(false);
                year_detail.setEditable(false);

                name_detail.setPromptText("");
                artist_detail.setPromptText("");
                album_detail.setPromptText("");
                year_detail.setPromptText("");

                cancel_button.setDisable(true);
                confirm_button.setDisable(true);
            }
        });
    }

    public void editSongHandle() {
        Song currentSongSelected = findSong(songs_listview.getSelectionModel().getSelectedItem().toString());
        int index = songs_listview.getSelectionModel().getSelectedIndex();
        name_detail.setEditable(true);
        artist_detail.setEditable(true);
        album_detail.setEditable(true);
        year_detail.setEditable(true);

        confirm_button.setDisable(false);
        cancel_button.setDisable(false);

        confirm_button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            //Need to check if song && artist already exist
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (isSongExists(name_detail.getText(), artist_detail.getText())) {
                    //Create dialog to show that song already exists
                } else {
                    currentSongSelected.setName(name_detail.getText());
                    currentSongSelected.setArtist(artist_detail.getText());
                    currentSongSelected.setAlbum(album_detail.getText());
                    currentSongSelected.setYear(Integer.parseInt(year_detail.getText()));

                    songsObservableList.set(index, currentSongSelected.getName());

                    name_detail.setEditable(false);
                    artist_detail.setEditable(false);
                    album_detail.setEditable(false);
                    year_detail.setEditable(false);

                    confirm_button.setDisable(true);
                    cancel_button.setDisable(true);

                    sortSongsList();
                    songs_listview.refresh();
                }

            }
        });
        cancel_button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                name_detail.setText(currentSongSelected.getName());
                artist_detail.setText(currentSongSelected.getArtist());

                if (currentSongSelected.getAlbum() != null) {
                    album_detail.setText(currentSongSelected.getAlbum());
                } else {
                    album_detail.setText("");
                }
                if (currentSongSelected.getYear() != -1) {
                    year_detail.setText(String.valueOf(currentSongSelected.getYear()));
                } else {
                    year_detail.setText("");
                }

                name_detail.setEditable(false);
                artist_detail.setEditable(false);
                album_detail.setEditable(false);
                year_detail.setEditable(false);

                confirm_button.setDisable(true);
                cancel_button.setDisable(true);
            }
        });
    }

    public void deleteSongHandle() {
        String currentSongName = songs_listview.getSelectionModel().getSelectedItem().toString();
        Song s = null;
        int index = -1;
        for (int i = 0; i < songsList.size(); i++) {
            if (songsList.get(i).getName().equals(currentSongName)) {
                songsList.remove(i);
            }
        }
        songsObservableList.removeAll(songsList);
        sortSongsList();
        songs_listview.getItems().clear();
        songs_listview.refresh();
        for (int i = 0; i < songsList.size(); i++) {
            songsObservableList.add(songsList.get(i).getName());
        }
        songs_listview.setItems(songsObservableList);

    }
    
    public void sortSongsList() {
        //Sort songsList alphabetically
        String s = songsList.get(0).getName();
        // s.compareTo()
        for (int i = 1; i < songsList.size(); i++) {
            int j = i-1;
            Song t = songList.get(i);
            
            while((t.getName().compareTo(songsList.get(j).getName()) < 0) && j>=0){
                songList.set(j+1,songList.get(j));  //shifting
                j--;   
            }     
            songList.set(j+1,t);        //correct position               
        }
    }

    }

    public void showSongDetails() {
        for (int i = 0; i < songsList.size(); i++) {
            if (songsList.get(i).getName().equals(songs_listview.getSelectionModel().getSelectedItem().toString())) {
                name_detail.setText(songsList.get(i).getName());
                artist_detail.setText(songsList.get(i).getArtist());

                if (songsList.get(i).getAlbum() != null) {
                    album_detail.setText(songsList.get(i).getAlbum());
                } else {
                    album_detail.setText("");
                }
                if (songsList.get(i).getYear() != -1) {
                    year_detail.setText(String.valueOf(songsList.get(i).getYear()));
                } else {
                    year_detail.setText("");
                }
            }
        }
    }

    public Song findSong(String songName) {
        for (int i = 0; i < songsList.size(); i++) {
            if (songsList.get(i).getName().equals(songName)) {
                return songsList.get(i);
            }
        }
        return null;
    }

    private boolean isSongExists(String name, String artist) {
        for (int i = 0; i < songsList.size(); i++) {
            if (songsList.get(i).getName().equals(name) && songsList.get(i).getArtist().equals(artist)) {
                return true;
            }
        }
        return false;
    }

    public void populateList() {
        songsList.add(new Song("Starboy", "The Weeknd"));
        songsList.add(new Song("Closer", "Chainsmokers", "album_here", 2016));
        for (int i = 0; i < songsList.size(); i++) {
            songsObservableList.add(songsList.get(i).getName());
        }
        songs_listview.setItems(songsObservableList);
        if (songsList != null) {
            songs_listview.getSelectionModel().selectFirst();
            showSongDetails();
        }
    }
}
