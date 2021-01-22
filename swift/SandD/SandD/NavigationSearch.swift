import SwiftUI
import UIKit

struct NavigationSearch: UIViewControllerRepresentable {
    typealias UIViewControllerType = Wrapper
    
    @Binding var text: String
    var placeholder: String
    
//    init(text: Binding<String>, placeholder: p) {
//        self._text = text
//        self.
//    }
//
    func makeCoordinator() -> Coordinator {
        Coordinator(representable: self)
    }

    func makeUIViewController(context: Context) -> Wrapper {
        Wrapper()
    }
    
    func updateUIViewController(_ wrapper: Wrapper, context: Context) {
        wrapper.searchController = context.coordinator.searchController
        wrapper.searchController?.searchBar.text = text
        wrapper.searchController?.searchBar.placeholder = placeholder
        wrapper.searchController?.automaticallyShowsCancelButton = false
        wrapper.navigationBarSizeToFit()
    }
    
    class Coordinator: NSObject, UISearchResultsUpdating {
        let representable: NavigationSearch
        
        let searchController: UISearchController
        
        init(representable: NavigationSearch) {
            self.representable = representable
            self.searchController = UISearchController(searchResultsController: nil)
            super.init()
            self.searchController.searchResultsUpdater = self
            self.searchController.obscuresBackgroundDuringPresentation = false
            self.searchController.searchBar.text = representable.text
        }
        
        // MARK: - UISearchResultsUpdating
        func updateSearchResults(for searchController: UISearchController) {
            guard let text = searchController.searchBar.text else { return }
            DispatchQueue.main.async { [weak self] in self?.representable.text = text }
        }
    }
    
    class Wrapper: UIViewController {
        var searchController: UISearchController? {
            get {
                self.parent?.navigationItem.searchController
            }
            set {
                self.parent?.navigationItem.searchController = newValue
            }
        }
        
        func navigationBarSizeToFit() {
            //self.searchController?.searchBar.sizeToFit()
            self.parent?.navigationController?.navigationBar.sizeToFit()
        }
    }
}
