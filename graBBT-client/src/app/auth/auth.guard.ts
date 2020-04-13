import { Injectable } from '@angular/core'
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router'
import { SessionService } from '../services/session.service'

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private sessionService: SessionService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.sessionService.getCustomerIsLogIn()) {
      return true
    } else {
      this.router.navigate(['/error'], {
        state: {
          error: 'Not logged in',
        },
      })
    }
  }
}
