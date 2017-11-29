import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestItems } from './request-items.model';
import { RequestItemsPopupService } from './request-items-popup.service';
import { RequestItemsService } from './request-items.service';
import { Product, ProductService } from '../product';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-request-items-dialog',
    templateUrl: './request-items-dialog.component.html'
})
export class RequestItemsDialogComponent implements OnInit {

    requestItems: RequestItems;
    isSaving: boolean;

    products: Product[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private requestItemsService: RequestItemsService,
        private productService: ProductService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productService.query()
            .subscribe((res: ResponseWrapper) => { this.products = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestItems.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestItemsService.update(this.requestItems));
        } else {
            this.subscribeToSaveResponse(
                this.requestItemsService.create(this.requestItems));
        }
    }

    private subscribeToSaveResponse(result: Observable<RequestItems>) {
        result.subscribe((res: RequestItems) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestItems) {
        this.eventManager.broadcast({ name: 'requestItemsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-request-items-popup',
    template: ''
})
export class RequestItemsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestItemsPopupService: RequestItemsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestItemsPopupService
                    .open(RequestItemsDialogComponent as Component, params['id']);
            } else {
                this.requestItemsPopupService
                    .open(RequestItemsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
