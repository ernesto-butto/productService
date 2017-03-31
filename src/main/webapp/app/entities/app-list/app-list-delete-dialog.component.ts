import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { AppList } from './app-list.model';
import { AppListPopupService } from './app-list-popup.service';
import { AppListService } from './app-list.service';

@Component({
    selector: 'jhi-app-list-delete-dialog',
    templateUrl: './app-list-delete-dialog.component.html'
})
export class AppListDeleteDialogComponent {

    appList: AppList;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private appListService: AppListService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['appList']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.appListService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'appListListModification',
                content: 'Deleted an appList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-app-list-delete-popup',
    template: ''
})
export class AppListDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private appListPopupService: AppListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.appListPopupService
                .open(AppListDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
