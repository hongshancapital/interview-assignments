//
//  ViewController.swift
//  HSSearchTextPrint
//
//  Created by dongxia zhu on 2021/10/19.
//

import UIKit

class ViewController: UIViewController, UISearchBarDelegate {

    @IBOutlet weak var mySearchBar: UISearchBar!
    var zTimer: Timer? = Timer()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        mySearchBar.delegate = self
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if (zTimer != nil) {
            destroyTimer()
        }
        zTimer = Timer.scheduledTimer(withTimeInterval: 1, repeats: false, block: {
            [weak self] _ in
            self?.doSometing(searchText)
        })
    }

    func doSometing(_ text: String) {
        print(text)
    }
    
    func destroyTimer() {
        if ((zTimer?.isValid)!) {
            zTimer?.invalidate()
        }
        zTimer = nil
    }
    
    deinit {
        destroyTimer()
    }


}

