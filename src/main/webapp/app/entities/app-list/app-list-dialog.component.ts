import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { AppList } from './app-list.model';
import { AppListPopupService } from './app-list-popup.service';
import { AppListService } from './app-list.service';
import { Product, ProductService } from '../product';
import { Tag, TagService } from '../tag';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-app-list-dialog',
    templateUrl: './app-list-dialog.component.html'
})
export class AppListDialogComponent implements OnInit {

    appList: AppList;
    authorities: any[];
    isSaving: boolean;

    products: Product[];

    tags: Tag[];

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private appListService: AppListService,
        private productService: ProductService,
        private tagService: TagService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['appList']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => { this.products = res.json(); }, (res: Response) => this.onError(res.json()));
        this.tagService.query().subscribe(
            (res: Response) => { this.tags = res.json(); }, (res: Response) => this.onError(res.json()));
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.appList.id !== undefined) {
            this.appListService.update(this.appList)
                .subscribe((res: AppList) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.appListService.create(this.appList)
                .subscribe((res: AppList) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: AppList) {
        this.eventManager.broadcast({ name: 'appListListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-app-list-popup',
    template: ''
})
export class AppListPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private appListPopupService: AppListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.appListPopupService
                    .open(AppListDialogComponent, params['id']);
            } else {
                this.modalRef = this.appListPopupService
                    .open(AppListDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
