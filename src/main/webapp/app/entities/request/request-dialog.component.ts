import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Request } from './request.model';
import { RequestPopupService } from './request-popup.service';
import { RequestService } from './request.service';
import { UserExtra, UserExtraService } from '../user-extra';
import { RequestItems, RequestItemsService } from '../request-items';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-request-dialog',
    templateUrl: './request-dialog.component.html'
})
export class RequestDialogComponent implements OnInit {

    request: Request;
    isSaving: boolean;

    userextras: UserExtra[];

    requestitems: RequestItems[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private requestService: RequestService,
        private userExtraService: UserExtraService,
        private requestItemsService: RequestItemsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userExtraService.query()
            .subscribe((res: ResponseWrapper) => { this.userextras = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.requestItemsService.query()
            .subscribe((res: ResponseWrapper) => { this.requestitems = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.request.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestService.update(this.request));
        } else {
            this.subscribeToSaveResponse(
                this.requestService.create(this.request));
        }
    }

    private subscribeToSaveResponse(result: Observable<Request>) {
        result.subscribe((res: Request) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Request) {
        this.eventManager.broadcast({ name: 'requestListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserExtraById(index: number, item: UserExtra) {
        return item.id;
    }

    trackRequestItemsById(index: number, item: RequestItems) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-request-popup',
    template: ''
})
export class RequestPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestPopupService: RequestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestPopupService
                    .open(RequestDialogComponent as Component, params['id']);
            } else {
                this.requestPopupService
                    .open(RequestDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
