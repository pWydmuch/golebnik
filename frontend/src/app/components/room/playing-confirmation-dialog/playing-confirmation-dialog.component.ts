import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material";
import {RoomService} from "../../../services/room.service";
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {WebSocketService} from "../../../services/web-socket.service";
import {PlayerDto} from "../player-dto";

@Component({
  selector: 'app-playing-confirmation-dialog',
  templateUrl: './playing-confirmation-dialog.component.html',
  styleUrls: ['./playing-confirmation-dialog.component.css']
})
export class PlayingConfirmationDialogComponent implements OnInit {

  private messageTitle: string = "click to start game"
  private stompClient;
  private roomId: string;
  private playerSessionId: string;
  private playersNumber: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private roomService: RoomService,
    public dialogRef: MatDialogRef<PlayingConfirmationDialogComponent>,
    public webSocketService: WebSocketService
  ) {
    this.roomId = data.roomId;
    this.playerSessionId = data.playerSessionId;
    this.playersNumber = data.playersNumber;
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/topic/rooms/${this.roomId}/players/confirmations`, payload => {
        const players: PlayerDto[] = JSON.parse(payload.body);
        if (this.allPlayersConfirmed(players)) {
          this.closeModal();
        }
      });
    });
  }

  startGame() {
    this.roomService.confirmPlayer(this.roomId, this.playerSessionId)
      .subscribe(() => this.messageTitle = "waiting for other players");


    // this.closeModal();
  }

  resign() {
    this.roomService.removePlayer(this.roomId, this.playerSessionId)
      .subscribe(() => this.closeModal())

  }

  // If the user clicks the cancel button a.k.a. the go back button, then\
  // just close the modal
  private closeModal() {
    this.dialogRef.close();
  }

  private allPlayersConfirmed(players: PlayerDto[]) {
    return players.filter(p => p.confirmationDone === true).length === this.playersNumber;
  }


}
