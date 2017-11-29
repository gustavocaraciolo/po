import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestItems } from './request-items.model';
import { RequestItemsPopupService } from './request-items-popup.service';
import { RequestItemsService } from './request-items.service';

@Component({
    selector: 'jhi-request-items-delete-dialog',
    templateUrl: './request-items-delete-dialog.component.html'
})
export class RequestItemsDeleteDialogComponent {

    requestItems: RequestItems;

    constructor(
        private requestItemsService: RequestItemsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestItemsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestItemsListModification',
                content: 'Deleted an requestItems'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-request-items-delete-popup',
    template: ''
})
export class RequestItemsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestItemsPopupService: RequestItemsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestItemsPopupService
                .open(RequestItemsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
