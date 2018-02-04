import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatRadioModule,MatSnackBarModule, MatDialogModule, MatProgressSpinnerModule, MatProgressBarModule, MatCardModule, MatInputModule, MatExpansionModule, MatSelectModule, MatGridListModule, MatSliderModule} from '@angular/material';

import{ RestService } from './rest.service' ;
import { AppComponent } from './app.component';
import { MasterComponent } from './master/master.component';
import { LoginComponent } from './login/login.component';

import {MatCheckboxModule} from '@angular/material/checkbox';
import { ThankyouDialogComponent } from './thankyou-dialog/thankyou-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    MasterComponent,
    LoginComponent,
    ThankyouDialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    MatRadioModule,
    MatProgressBarModule,
    MatCardModule,
    MatExpansionModule,
    MatSelectModule,
    MatGridListModule,
    MatSliderModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule
  ],
  providers: [RestService],
  bootstrap: [AppComponent],
  entryComponents: [ThankyouDialogComponent]

})
export class AppModule { }
