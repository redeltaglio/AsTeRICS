﻿/*
 *    AsTeRICS - Assistive Technology Rapid Integration and Construction Set
 * 
 * 
 *        d8888      88888888888       8888888b.  8888888 .d8888b.   .d8888b. 
 *       d88888          888           888   Y88b   888  d88P  Y88b d88P  Y88b
 *      d88P888          888           888    888   888  888    888 Y88b.     
 *     d88P 888 .d8888b  888   .d88b.  888   d88P   888  888         "Y888b.  
 *    d88P  888 88K      888  d8P  Y8b 8888888P"    888  888            "Y88b.
 *   d88P   888 "Y8888b. 888  88888888 888 T88b     888  888    888       "888
 *  d8888888888      X88 888  Y8b.     888  T88b    888  Y88b  d88P Y88b  d88P
 * d88P     888  88888P' 888   "Y8888  888   T88b 8888888 "Y8888P"   "Y8888P" 
 *
 *
 *                    homepage: http://www.asterics.org 
 *
 *         This project has been partly funded by the European Commission, 
 *                      Grant Agreement Number 247730
 *  
 *  
 *    License: LGPL v3.0 (GNU Lesser General Public License Version 3.0)
 *                 http://www.gnu.org/licenses/lgpl.html
 * 
 * --------------------------------------------------------------------------------
 * AsTeRICS – EC Grant Agreement No. 247730
 * Assistive Technology Rapid Integration and Construction Set
 * --------------------------------------------------------------------------------
 * Filename: RecentFiles.cs
 * Class(es):
 *   Classname: RecentFiles
 *   Description: Container to store the list of recently used files.
 * Author: Roland Ossmann
 * Date: 01.02.2011
 * Version: 0.1
 * Comments:
 * --------------------------------------------------------------------------------
 */
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections.ObjectModel;
using Microsoft.Windows.Controls.Ribbon;

namespace Asterics.ACS {
   public class RecentFiles : ObservableCollection<RibbonGalleryItem> {
        public RecentFiles() : base() {
            ClearItems();
        }

        public void AddItem(RibbonGalleryItem recentFileItem) {
            Add(recentFileItem);
        }

    } 
}
