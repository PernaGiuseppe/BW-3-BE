import { Container } from "react-bootstrap";

export default function Footer() {
  return (
    <footer className="bg-dark text-white mt-auto py-4">
      <Container>
        <div className="row">
          <div className="col-md-4">
            <h5>EPIC ENERGY SERVICES</h5>
            <p className="small">
              Gestione clienti e fatture per servizi energetici
            </p>
          </div>
          <div className="col-md-4">
            <h6>Link Utili</h6>
            <ul className="list-unstyled">
              <li>
                <a href="/clienti" className="text-white-50">
                  Clienti
                </a>
              </li>
              <li>
                <a href="/fatture" className="text-white-50">
                  Fatture
                </a>
              </li>
            </ul>
          </div>
          <div className="col-md-4">
            <h6>Contatti</h6>
            <p className="small text-white-50">
              Email: info@epicenergy.it
              <br />
              Tel: +39 02 1234567
            </p>
          </div>
        </div>
        <hr className="bg-white-50" />
        <div className="text-center small text-white-50">
          Â© {new Date().getFullYear()} Epic Energy Services. Tutti i diritti
          riservati.
        </div>
      </Container>
    </footer>
  );
}
