import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RequestItems } from './request-items.model';
import { RequestItemsService } from './request-items.service';

@Component({
    selector: 'jhi-request-items-detail',
    templateUrl: './request-items-detail.component.html'
})
export class RequestItemsDetailComponent implements OnInit, OnDestroy {

    requestItems: RequestItems;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestItemsService: RequestItemsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestItems();
    }

    load(id) {
        this.requestItemsService.find(id).subscribe((requestItems) => {
            this.requestItems = requestItems;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestItemsListModification',
            (response) => this.load(this.requestItems.id)
        );
    }
}
