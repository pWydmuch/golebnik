import {PlayerDto} from "./player-dto";

export class RoomDto {
  roomId : string;
  activityManagerId : string;
  roomUsers : PlayerDto[];
}
