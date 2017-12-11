import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimestampsComponent } from './timestamps.component';

describe('TimestampsComponent', () => {
  let component: TimestampsComponent;
  let fixture: ComponentFixture<TimestampsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimestampsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimestampsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
