import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AppListDetailComponent } from '../../../../../../main/webapp/app/entities/app-list/app-list-detail.component';
import { AppListService } from '../../../../../../main/webapp/app/entities/app-list/app-list.service';
import { AppList } from '../../../../../../main/webapp/app/entities/app-list/app-list.model';

describe('Component Tests', () => {

    describe('AppList Management Detail Component', () => {
        let comp: AppListDetailComponent;
        let fixture: ComponentFixture<AppListDetailComponent>;
        let service: AppListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [AppListDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    AppListService
                ]
            }).overrideComponent(AppListDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppListService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AppList(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.appList).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
