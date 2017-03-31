import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductImageComponent } from './product-image.component';
import { ProductImageDetailComponent } from './product-image-detail.component';
import { ProductImagePopupComponent } from './product-image-dialog.component';
import { ProductImageDeletePopupComponent } from './product-image-delete-dialog.component';

import { Principal } from '../../shared';


export const productImageRoute: Routes = [
  {
    path: 'product-image',
    component: ProductImageComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.productImage.home.title'
    }
  }, {
    path: 'product-image/:id',
    component: ProductImageDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.productImage.home.title'
    }
  }
];

export const productImagePopupRoute: Routes = [
  {
    path: 'product-image-new',
    component: ProductImagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.productImage.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'product-image/:id/edit',
    component: ProductImagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.productImage.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'product-image/:id/delete',
    component: ProductImageDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'productServiceApp.productImage.home.title'
    },
    outlet: 'popup'
  }
];
