/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PoseidonTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RequestItemsDetailComponent } from '../../../../../../main/webapp/app/entities/request-items/request-items-detail.component';
import { RequestItemsService } from '../../../../../../main/webapp/app/entities/request-items/request-items.service';
import { RequestItems } from '../../../../../../main/webapp/app/entities/request-items/request-items.model';

describe('Component Tests', () => {

    describe('RequestItems Management Detail Component', () => {
        let comp: RequestItemsDetailComponent;
        let fixture: ComponentFixture<RequestItemsDetailComponent>;
        let service: RequestItemsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PoseidonTestModule],
                declarations: [RequestItemsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RequestItemsService,
                    JhiEventManager
                ]
            }).overrideTemplate(RequestItemsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestItemsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestItemsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RequestItems(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.requestItems).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
