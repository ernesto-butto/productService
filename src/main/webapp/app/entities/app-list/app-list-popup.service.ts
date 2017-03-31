import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AppList } from './app-list.model';
import { AppListService } from './app-list.service';
@Injectable()
export class AppListPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private appListService: AppListService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.appListService.find(id).subscribe(appList => {
                if (appList.registration) {
                    appList.registration = {
                        year: appList.registration.getFullYear(),
                        month: appList.registration.getMonth() + 1,
                        day: appList.registration.getDate()
                    };
                }
                this.appListModalRef(component, appList);
            });
        } else {
            return this.appListModalRef(component, new AppList());
        }
    }

    appListModalRef(component: Component, appList: AppList): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.appList = appList;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
