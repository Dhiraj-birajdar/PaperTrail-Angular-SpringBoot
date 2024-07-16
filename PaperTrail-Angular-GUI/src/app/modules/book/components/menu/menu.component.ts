import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "../../../../services/keycloak/keycloak.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  firstname = 'user';

  async assignProfileName(){
     const parsedToken =  this.keycloakService.keycloak?.tokenParsed;
    if (parsedToken) {
      this.firstname = parsedToken['given_name'];
    }
  }

  constructor(
    private keycloakService: KeycloakService
  ) {
  }

  ngOnInit(): void {
    this.assignProfileName();
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')) {
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach(nav => {
          nav.classList.remove('active')
        })
        link.classList.add('active');
      });
    });
  }

  async signOut() {
    this.keycloakService.logout();
  }
}
