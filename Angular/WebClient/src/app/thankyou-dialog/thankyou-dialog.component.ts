import { Component, OnInit, Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-thankyou-dialog',
  templateUrl: './thankyou-dialog.component.html',
  styleUrls: ['./thankyou-dialog.component.css']
})
export class ThankyouDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ThankyouDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}