import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AppListComponent } from './app-list.component';
import { AppListDetailComponent } from './app-list-detail.component';
import { AppListPopupComponent } from './app-list-dialog.component';
import { AppListDeletePopupComponent } from './app-list-delete-dialog.component';

import { Principal } from '../../shared';


export const appListRoute: Routes = [
  {
    path: 'app-list',
    component: AppListComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.appList.home.title'
    }
  }, {
    path: 'app-list/:id',
    component: AppListDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.appList.home.title'
    }
  }
];

export const appListPopupRoute: Routes = [
  {
    path: 'app-list-new',
    component: AppListPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.appList.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'app-list/:id/edit',
    component: AppListPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.appList.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'app-list/:id/delete',
    component: AppListDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.appList.home.title'
    },
    outlet: 'popup'
  }
];
