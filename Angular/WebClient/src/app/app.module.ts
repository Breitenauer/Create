import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatRadioModule, MatProgressBarModule, MatCardModule, MatInputModule, MatExpansionModule, MatSelectModule, MatGridListModule, MatSliderModule} from '@angular/material';

import{ RestService } from './rest.service' ;
import { AppComponent } from './app.component';
import { MasterComponent } from './master/master.component';
import { UploadComponent } from './upload/upload.component';
import { LoginComponent } from './login/login.component';
import { TimestampsComponent } from './timestamps/timestamps.component';

@NgModule({
  declarations: [
    AppComponent,
    MasterComponent,
    UploadComponent,
    LoginComponent,
    TimestampsComponent
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
    MatSliderModule
  ],
  providers: [RestService],
  bootstrap: [AppComponent]
})
export class AppModule { }
