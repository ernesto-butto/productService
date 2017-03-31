import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils } from 'ng-jhipster';
import { ProductImage } from './product-image.model';
import { ProductImageService } from './product-image.service';

@Component({
    selector: 'jhi-product-image-detail',
    templateUrl: './product-image-detail.component.html'
})
export class ProductImageDetailComponent implements OnInit, OnDestroy {

    productImage: ProductImage;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private productImageService: ProductImageService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['productImage']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.productImageService.find(id).subscribe(productImage => {
            this.productImage = productImage;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
