import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { AppList } from './app-list.model';
import { AppListService } from './app-list.service';

@Component({
    selector: 'jhi-app-list-detail',
    templateUrl: './app-list-detail.component.html'
})
export class AppListDetailComponent implements OnInit, OnDestroy {

    appList: AppList;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private appListService: AppListService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['appList']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.appListService.find(id).subscribe(appList => {
            this.appList = appList;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
