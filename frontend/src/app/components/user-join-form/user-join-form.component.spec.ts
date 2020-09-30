import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserJoinFormComponent } from './user-join-form.component';

describe('UserJoinFormComponent', () => {
  let component: UserJoinFormComponent;
  let fixture: ComponentFixture<UserJoinFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserJoinFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserJoinFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
