import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RequestItemsComponent } from './request-items.component';
import { RequestItemsDetailComponent } from './request-items-detail.component';
import { RequestItemsPopupComponent } from './request-items-dialog.component';
import { RequestItemsDeletePopupComponent } from './request-items-delete-dialog.component';

@Injectable()
export class RequestItemsResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const requestItemsRoute: Routes = [
    {
        path: 'request-items',
        component: RequestItemsComponent,
        resolve: {
            'pagingParams': RequestItemsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestItems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-items/:id',
        component: RequestItemsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestItemsPopupRoute: Routes = [
    {
        path: 'request-items-new',
        component: RequestItemsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-items/:id/edit',
        component: RequestItemsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-items/:id/delete',
        component: RequestItemsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
