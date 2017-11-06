import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {HttpClientModule} from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatRadioModule, MatProgressBarModule, MatCardModule} from '@angular/material';

import{ RestService } from './rest.service' ;
import { AppComponent } from './app.component';
import { MasterComponent } from './master/master.component';

@NgModule({
  declarations: [
    AppComponent,
    MasterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatRadioModule,
    MatProgressBarModule,
    MatCardModule
  ],
  providers: [RestService],
  bootstrap: [AppComponent]
})
export class AppModule { }
